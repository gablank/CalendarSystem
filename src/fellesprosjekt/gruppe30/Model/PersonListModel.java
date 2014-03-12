package fellesprosjekt.gruppe30.Model;

import javax.swing.*;

public class PersonListModel extends DefaultListModel {

    public void firePersonChanged(InternalUser user) {
        fireContentsChanged(this, indexOf(user), indexOf(user));
    }
}
