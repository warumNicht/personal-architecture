package architecture.domain.models.serviceModels;

public abstract class BaseServiceModel {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
