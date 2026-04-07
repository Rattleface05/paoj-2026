package com.pao.laboratory07.exercise1;

import com.pao.laboratory07.exercise1.exceptions.CannotCancelFinalOrderException;
import com.pao.laboratory07.exercise1.exceptions.CannotRevertInitialOrderStateException;
import com.pao.laboratory07.exercise1.exceptions.OrderIsAlreadyFinalException;

import java.util.Stack;

public class Order {
    OrderState state;
    Stack<OrderState> history;

    Order(OrderState state) {
        this.state = state;
        //System.out.println(state);
        this.history = new Stack<OrderState>();
        history.push(state);
    }

    void nextState() {
        switch (state) {
            case PLACED -> {
                history.push(state);
                state = OrderState.PROCESSED;
                System.out.println("Order state updated to: "+state);
            }
            case PROCESSED -> {
                history.push(state);
                state = OrderState.SHIPPED;

                System.out.println("Order state updated to: "+state);
            }
            case SHIPPED -> {
                history.push(state);
                state = OrderState.DELIVERED;
                System.out.println("Order state updated to: "+state);
            }
            case DELIVERED, CANCELED -> {
                throw new OrderIsAlreadyFinalException("Order is final!");
            }
        }
    }

    void cancel() {
        switch (state) {
            case PLACED, PROCESSED, SHIPPED -> {
                history.push(state);
                state = OrderState.CANCELED;
                System.out.println("Order has been canceled.");
            }
            case DELIVERED, CANCELED -> {
                throw new CannotCancelFinalOrderException("Cannot cancel final order!");
            }
        }

    }

    void undoState() {
        if (history.isEmpty()) {
            throw (new CannotRevertInitialOrderStateException("No initial state!"));
        } else {
            state = history.pop();
            System.out.println("Order state reverted to: "+state);

        }
    }
}



