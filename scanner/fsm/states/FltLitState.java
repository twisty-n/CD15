package scanner.fsm.states;

import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/9/2015
 * File Name:       FltLitState
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class FltLitState extends State {

    public FltLitState(StateMachine executionContext) {
        super(executionContext);
    }

    @Override
    public void execute() {

    }

    @Override
    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.FLT_LIT_STATE;
    }
}
