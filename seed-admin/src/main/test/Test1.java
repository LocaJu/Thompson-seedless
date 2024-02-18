import com.seed.BlogAdminApplication;
import com.seed.domain.entity.system.SysConfig;
import com.seed.service.system.ISysConfigService;
import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.Iterator;

@Slf4j
@SpringBootTest(classes = BlogAdminApplication.class)
public class Test1 {

    @Autowired
    private MeterRegistry meterRegistry;

    @Test
    public void getSysCpuUsed() {
        Meter processCpuUsedMeter = this.meterRegistry.find("system.cpu.usage").meter();
        Iterable<Measurement> processCpuUsedMeasure = processCpuUsedMeter.measure();
        Iterator<Measurement> processCpuUsedMeasureIterator = processCpuUsedMeasure.iterator();
        Measurement processCpuUsedMeasureNext = (Measurement)processCpuUsedMeasureIterator.next();
        Double v= processCpuUsedMeasureNext.getValue();
        log.info("cpu使用率："+v);
    }

    @Autowired
    ISysConfigService sysConfigService;
    @Test
    public void getsystem(){
        SysConfig sysConfig = sysConfigService.selectConfigById(1L);
        log.info(sysConfig.getConfigValue());
    }

    @Autowired
    DataSource dataSource;
    @Test
    public void getDatabaseHealthInfo() {
        DataSourceHealthIndicator dataSourceHealthIndicator = new DataSourceHealthIndicator(dataSource);
        HealthComponent datasourceHealth = dataSourceHealthIndicator.health();
        log.info("数据库状态："+datasourceHealth.getStatus().toString());
    }
}
