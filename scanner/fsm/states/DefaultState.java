package scanner.fsm.states;

import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import scanner.tokenizer.SignificantCharacters;

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

        } else if ( underConsideration == '\t' || underConsideration == ' ' ) {

            // We are considering just simple whitespace. Not proceeding a new line
            // So consume and stay here
            this.getExecutionContext().readNextCharacter();
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));
            return;

        } else if ( underConsideration == '\n' ) {

            // Ignore and consume for now, but we will eventually go to multi-line comment handling
            this.getExecutionContext().readNextCharacter();
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));
            return;

        } else if ( Character.isDigit(underConsideration) && underConsideration != '0' ) {

            // We have the beginnings of an integer or floating point literal
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.INT_LIT_STATE));

        } else if ( underConsideration == '0' ) {

            // Need to handle where its just a 0
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.ZERO_INT_LIT_STATE));

        } else if ( SignificantCharacters.isGeneralMultiCharOpComponent( underConsideration ) ) {

            // Transition to generalized multi-op component state
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.SEEN_MULTIOP_COMP_STATE));

        } else if ( SignificantCharacters.isOperatorOrDelimiter( underConsideration )
                    && ! SignificantCharacters.isGeneralMultiCharOpComponent ( underConsideration )) {

            this.getExecutionContext().setNextState(StateManager.getState( StateManager.StateClass.SEEN_STANDALONE_OP_OR_DELIM ));

        } else if (underConsideration == SignificantCharacters.EXCLAM.asChar()) {

            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.SEEN_EXCLAM_STATE));

        } else if (underConsideration == SignificantCharacters.SLASH_OP.asChar()) {

            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.SEEN_SLASH_STATE));

        }

    }

    @Override
    public void exitState() {

    }

    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.START_STATE;
    }
}