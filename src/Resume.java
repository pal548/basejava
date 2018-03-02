/**
 * com.urise.webapp.model.Resume class
 */
public class Resume {

    Resume() {
    }

    Resume(String uuid) {
        this.uuid = uuid;
    }

    // Unique identifier
    String uuid;

    @Override
    public String toString() {
        return uuid;
    }
}
