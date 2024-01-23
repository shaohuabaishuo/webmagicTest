package zhuanghuadiancang;

import lombok.Getter;

@Getter
public enum SearchTypeEnum {

    START_SEARCH("开始搜索"),
    BOOK_NAME("书名搜索结果"),
    CHAPTER_NAME("章节搜索结果");

    SearchTypeEnum(String desc) {
        this.desc = desc;
    }

    private String desc;

}
