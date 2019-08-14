#!/bin/sh

function print_current_version() {
	CURRENT_VERSION=`awk 'BEGIN {versionFound = 0;} /<version>/ {if (versionFound == 0) {print $0; versionFound = 1;}}' pom.xml  | awk -F\> '{print$2}' | awk -F\< '{print $1}'`
	echo $CURRENT_VERSION
}

function set_version() {
	echo "setting version to|" $1
	awk -v version="$1" 'BEGIN {versionFound = 0;} {if (match($0, "version") > 0 && versionFound == 0) {print "<version>" version "</version>"; versionFound = 1;} else {print $0}}' pom.xml > pom_tmp.xml
	mv pom_tmp.xml pom.xml
}

function commit_version() {
	echo "committing version|msg|" $*
	git commit -am "$*"
}

if [ -z "$1" ]
  then
    echo "argument missing: release version"
    echo "current version|"`print_current_version`
    exit 1
fi
RELEASE_VERSION=$1

if [ -z "$2" ]
  then
    echo "argument missing: next version (note: suffix -SNAPSHOT will be added automatically)"
    echo "current version|"`print_current_version`
    exit 1
fi
NEXT_VERSION=$2"-SNAPSHOT"

echo "bumping current version|"`print_current_version`"|to|"$RELEASE_VERSION"|next|"$NEXT_VERSION
read -p "Are you sure? " -n 1 -r
echo    # (optional) move to a new line
if [[ ! $REPLY =~ ^[Yy]$ ]]
then
    [[ "$0" = "$BASH_SOURCE" ]] && exit 1 || return 1 # handle exits from shell or function but don't exit interactive shell
fi

echo "ok, lets go!"

echo "setting release version|"$RELEASE_VERSION
set_version $RELEASE_VERSION
COMMIT_RELEASE_COMMENT="released v"$RELEASE_VERSION
commit_version $COMMIT_RELEASE_COMMENT

RELEASE_TAG="v"`echo $RELEASE_VERSION | sed 's/\.//g'`
echo "setting tag|"$RELEASE_TAG
git tag -a $RELEASE_TAG -m "$RELEASE_TAG"

echo "setting dev version|"$NEXT_VERSION
set_version $NEXT_VERSION
commit_version "bumped to dev version"

echo "done."
echo
echo "Tip: REMEMBER to push the new tag as well."
exit 0