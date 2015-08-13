package scanner.fsm.states;

import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    13/08/15
 * File Name:       SeenStandaloneOpOrDelim
 * Project Name:    CD15
 * Description:
 */
public class SeenStandaloneOpOrDelim extends State {

    public SeenStandaloneOpOrDelim ( StateMachine executionContext ) {
        super ( executionContext );
    }

    @Override
    public void execute () {

        // At this point, the standalone operator or delimiter has been read into the lexeme
        // because it is standalone, regardless of the following characters, we will terminate it
        // and say that it is valid here

        this.getExecutionContext().exposeLexeme().setIsComplete(
                true,
                this.getExecutionContext ().getCharacterForConsideration().getIndexOnLine (),
                true
        );
        this.getExecutionContext().readNextCharacter();
        this.getExecutionContext().setNextState( StateManager.getState ( StateManager.StateClass.START_STATE) );

    }

    @Override
    public StateManager.StateClass getStateClass () {
        return StateManager.StateClass.SEEN_STANDALONE_OP_OR_DELIM;
    }
}
