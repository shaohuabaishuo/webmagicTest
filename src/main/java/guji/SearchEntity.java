package guji;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchEntity {

    private String url;
    private String title;
    private String content;

    public void setTitle(String title) {
        this.title = title.replace("<em>","").replace("</em>","");
    }
}
