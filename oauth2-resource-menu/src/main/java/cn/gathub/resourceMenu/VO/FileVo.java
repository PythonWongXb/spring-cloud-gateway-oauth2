package cn.gathub.resourceMenu.VO;

import cn.gathub.resourceMenu.domain.ProjectDateFile;
import lombok.Data;

import java.util.List;

@Data
public class FileVo {
    private String date;
    private List<ProjectDateFile> list;
}
