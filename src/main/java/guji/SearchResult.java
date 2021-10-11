package guji;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResult {

    private String code;
    private SearchData data;

    @Getter
    @Setter
    public static class SearchData{
        private List<SearchEntity> items;
    }
}
