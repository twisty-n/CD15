package scanner.fsm.states;

import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import scanner.tokenizer.SignificantCharacter;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       StartState
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class DefaultState extends State {

    /**
     * Constructor
     * @param exectionContext
     */
    public DefaultState(StateMachine exectionContext) {
        super(exectionContext);
    }

    public void enterState() {
        // Do nothing. We dont want to write values when entering this state
    }

    @Override
    public void execute() {


        char underConsideration = this.getExecutionContext().getCharacterForConsideration().getCharacter();

        // Let the games begin. May the odds be ever in your favour
        // If the character is a letter, go to the IDT_KEY_STATE
        if ( Character.isLetter(underConsideration) ) {
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.IDT_KEY_STATE));
            return;

        } else if ( underConsideration == SignificantCharacter.TAB.asChar()
                || underConsideration == SignificantCharacter.SPACE.asChar() ) {

            // We are considering just simple whitespace. Not proceeding a new line
            // So consume and stay here
            this.getExecutionContext().readNextCharacter();
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));
            return;

        } else if ( underConsideration == SignificantCharacter.NEW_LINE.asChar() ) {

            // Ignore and consume for now, but we will eventually go to multi-line comment handling
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.PREP_FOR_MLC_STATE));
            return;

        } else if ( Character.isDigit(underConsideration)
                && underConsideration != SignificantCharacter.ZERO.asChar() ) {

            // We have the beginnings of an integer or floating point literal
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.INT_LIT_STATE));

        } else if ( underConsideration == SignificantCharacter.ZERO.asChar() ) {

            // Need to handle where its just a 0
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.ZERO_INT_LIT_STATE));

        } else if ( underConsideration == SignificantCharacter.QUOTE.asChar() ) {

            // Handle the case where its a string constant
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.STRING_CONSTANT_STATE));

        } else if ( SignificantCharacter.isGeneralMultiCharOpComponent(underConsideration) ) {

            // Transition to generalized multi-op component state to handle ops or delims that may be
            // part of ops like += or !=
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.SEEN_MULTIOP_COMP_STATE));

        } else if ( SignificantCharacter.isOperatorOrDelimiter(underConsideration)
                    && ! SignificantCharacter.isGeneralMultiCharOpComponent(underConsideration)
                    && ! (underConsideration == SignificantCharacter.SLASH_OP.asChar())
            ) {

            // Transition to standalone operator or delimiter state to handle standalone
            // operators: they aren't part of multicomponent operators
            this.getExecutionContext().setNextState(StateManager.getState( StateManager.StateClass.SEEN_STANDALONE_OP_OR_DELIM ));

        } else if (underConsideration == SignificantCharacter.EXCLAM.asChar()) {

            // We've seen an exclamation mark, transition to handler
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.SEEN_EXCLAM_STATE));

        } else if (underConsideration == SignificantCharacter.SLASH_OP.asChar() ) {

            // We've seen a slash, so its either an operator, or a line comment. handle it!
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.SEEN_SLASH_STATE));

        } else {

            // We've found some sort of illegal character. Execute an error chewer until we find a valid
            // place to continue from
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.ILLEGAL_CHARACTER_STATE));

        }

    }

    @Override
    public void exitState() {

    }

    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.START_STATE;
    }
}
