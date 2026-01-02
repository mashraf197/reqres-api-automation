package reqres.models;

public class UpdateUserRequest {
    public String name;
    public String job;

    public UpdateUserRequest(String name, String job) {
        this.name = name;
        this.job = job;
    }
}
