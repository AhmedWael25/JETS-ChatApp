package jets.chatserver.gui.helpers;


import jets.chatserver.gui.models.CurrentUserModel;

public class ModelsFactory {

    private static final ModelsFactory instance = new ModelsFactory();

    private CurrentUserModel currentUserModel;

    private ModelsFactory () { }

    public static ModelsFactory getInstance() {
        return instance;
    }

    public CurrentUserModel getCurrentUserModel() {
        if (currentUserModel == null) {
            currentUserModel = new CurrentUserModel();
        }
        return currentUserModel;
    }

}
