package com.ml_text_utils.nlp.impl.snowball;

import org.tartarus.snowball.SnowballProgram;

@FunctionalInterface
public interface SnowballProgramFactory {

    SnowballProgram buildSnowballProgram();

}
