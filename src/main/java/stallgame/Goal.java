package stallgame;

import stallgame.action.Actions;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

public enum Goal {

    TO_STALL_BUY {
        @Override
        public List<Actions> getActions(Role role) {
            if (Role.NO_ROLE.equals(role)) {
                return new LinkedList<>(asList(Actions.ENTER_STALL, Actions.REVIEW_INVENTORY));
            }
            if (Role.PASSENGER.equals(role)) {

            }
            if (Role.VAGRANT.equals(role)) {

            }
            if (Role.VISITOR.equals(role)) {
                return new LinkedList<>(asList(Actions.BUY, Actions.LEAVE_STALL, Actions.BECOME_SELLER));
            }
            if (Role.BUYER.equals(role)) {

            }
            if (Role.WORKER.equals(role)) {

            }
            if (Role.SELLER.equals(role)) {
                return new LinkedList<>(asList(Actions.LEAVE_AND_LOCK_SELLER_PLACE, Actions.LEAVE_SELLER_PLACE));
            }
            return Collections.emptyList();
        }
    }, TO_STALL_VISIT {
        @Override
        public List<Actions> getActions(Role role) {
            if (Role.NO_ROLE.equals(role)) {
                return new LinkedList<>(asList(Actions.ENTER_STALL));
            }
            if (Role.PASSENGER.equals(role)) {

            }
            if (Role.VAGRANT.equals(role)) {

            }
            if (Role.VISITOR.equals(role)) {
                return new LinkedList<>(asList(Actions.BUY));
            }
            if (Role.BUYER.equals(role)) {

            }
            if (Role.WORKER.equals(role)) {

            }
            if (Role.SELLER.equals(role)) {

            }
            return Collections.emptyList();
        }
    }, TO_WORK {
        @Override
        public List<Actions> getActions(Role role) {
            if (Role.NO_ROLE.equals(role)) {
                return new LinkedList<>(asList(Actions.ENTER_STALL));
            }
            if (Role.PASSENGER.equals(role)) {

            }
            if (Role.VAGRANT.equals(role)) {

            }
            if (Role.VISITOR.equals(role)) {
                return new LinkedList<>(asList(Actions.BUY));
            }
            if (Role.BUYER.equals(role)) {

            }
            if (Role.WORKER.equals(role)) {

            }
            if (Role.SELLER.equals(role)) {

            }
            return Collections.emptyList();
        }
    }, TO_HOME {
        @Override
        public List<Actions> getActions(Role role) {
            if (Role.NO_ROLE.equals(role)) {
                return new LinkedList<>(asList(Actions.ENTER_STALL));
            }
            if (Role.PASSENGER.equals(role)) {

            }
            if (Role.VAGRANT.equals(role)) {

            }
            if (Role.VISITOR.equals(role)) {
                return new LinkedList<>(asList(Actions.BUY));
            }
            if (Role.BUYER.equals(role)) {

            }
            if (Role.WORKER.equals(role)) {

            }
            if (Role.SELLER.equals(role)) {

            }
            return Collections.emptyList();
        }
    };

    public abstract List<Actions> getActions(Role role);

}
