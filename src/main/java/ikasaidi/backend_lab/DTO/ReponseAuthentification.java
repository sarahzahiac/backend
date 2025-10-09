package ikasaidi.backend_lab.DTO;

public class ReponseAuthentification {
    private boolean success;
    private String message;
    private Integer id;
    private String name;
    private String token;

    public ReponseAuthentification(boolean success, String message, Integer id, String name, String token) {
        this.success = success;
        this.message = message;
        this.id = id;
        this.name = name;
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}