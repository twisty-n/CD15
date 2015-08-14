package scanner.fsm.states;

import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import scanner.tokenizer.SignificantCharacter;
import scanner.tokenizer.TokenClass;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       IDT_KEY_STATE
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class IdtKeyState extends State{

    /**
     * Constructor setting up our execution context
     *
     * @param executionContext
     */
    public IdtKeyState(StateMachine executionContext) {
        super(executionContext);
    }

    @Override
    public void execute() {

        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char underConsideration = charObj.getCharacter();

        // Loop while we continue to see characters or numbers
        if ( Character.isLetterOrDigit(underConsideration) ) {
            // We are able to loop so return early
            this.getExecutionContext().setNextState ( StateManager.getState ( StateManager.StateClass.IDT_KEY_STATE ) );
            return;
        }  else if ( SignificantCharacter.isOperatorOrDelimiter(underConsideration) ) {
           this.acceptIdent(charObj);
        } else if ( Character.isWhitespace( underConsideration ) ) {
            this.acceptIdent(charObj);
        } else if ( SignificantCharacter.isOperatorOrDelimiter(underConsideration) ||
                    underConsideration == SignificantCharacter.EXCLAM.asChar() ) {
           this.acceptIdent(charObj);
        } else {
            // Its some form of illegal symbol enter the error chewer
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.GEN_ERR_CHEW_STATE));
        }

    }

    private void acceptIdent(ReturnCharacter charObj) {

        this.getExecutionContext().exposeLexeme().setIsComplete(
                true,
                charObj.getIndexOnLine() - 1,
                true, TokenClass.TIDNT
        );
        this.getExecutionContext().setNextState( StateManager.getState( StateManager.StateClass.START_STATE ) );

    }

    @Override
    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.IDT_KEY_STATE;
    }
}
