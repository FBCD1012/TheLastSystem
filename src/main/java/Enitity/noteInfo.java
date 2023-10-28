package Enitity;


import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;

@Data
@Accessors(chain = true)
public class noteInfo {
    Integer noteId;
    String noteName;
    Date notedTime;
    String author;
    Integer authorId;
}
