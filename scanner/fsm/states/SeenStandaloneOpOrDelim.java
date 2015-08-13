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

    }

    @Override
    public StateManager.StateClass getStateClass () {
        return null;
    }
}
