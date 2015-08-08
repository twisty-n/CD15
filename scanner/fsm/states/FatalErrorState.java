package scanner.fsm.states;

import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import utils.DebugWriter;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       FatalErrorState
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class FatalErrorState extends State {

    public FatalErrorState(StateMachine ctx) {
        super(ctx);
        DebugWriter.writeEverywhere("FATAL ERROR: FSM in FatalErrorState state");
    }

    @Override
    public void enterState() {
        DebugWriter.writeEverywhere("FATAL ERROR: FSM attempting to enter FatalErrorState");
    }

    @Override
    public void execute() {
        DebugWriter.writeEverywhere("FATAL ERROR: FSM attempting to execute FatalErrorState");
    }

    @Override
    public void exitState() {
        DebugWriter.writeEverywhere("FATAL ERROR: FSM attempting to exit FatalErrorState");
    }

    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.FATAL_ERROR_STATE;
    }
}
