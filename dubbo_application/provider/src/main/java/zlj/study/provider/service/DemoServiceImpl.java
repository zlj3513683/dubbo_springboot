package zlj.study.provider.service;

import com.api.service.DemoService;
import org.apache.dubbo.config.annotation.Service;

/**
 * 功能：
 *
 * @author zoulinjun
 * @date 2020/11/28
 */
@Service(version = "1.0.0",interfaceClass = DemoService.class)
public class DemoServiceImpl implements DemoService {

    @Override
    public String ins() {
        return "新增成功";
    }

    @Override
    public String del() {
        return "删除成功";
    }

    @Override
    public String upd() {
        return "修改成功";
    }

    @Override
    public String sel() {
        return "查询成功";
    }
}
