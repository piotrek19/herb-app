package pl.herbs.backing_beans;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name = "light";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
