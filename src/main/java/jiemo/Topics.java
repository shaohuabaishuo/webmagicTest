package jiemo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class Topics {
    private String content;
    private Integer create_time;
    private LocalDateTime time;
}
