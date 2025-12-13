import com.seed.BlogAdminApplication;
import com.seed.domain.entity.system.SysConfig;
import com.seed.service.system.ISysConfigService;
import com.seed.service.system.web.service.SysPasswordService;
import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.security.SecureRandom;
import java.util.Iterator;

import static org.springframework.security.crypto.bcrypt.BCrypt.gensalt;

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

    @Test
    public void password(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pw= passwordEncoder.encode("admin123");
        System.out.println(pw);
    }

    public static void main(String[] args) {

        String s= gensalt("$2a",10);
        SecureRandom seedGen = new SecureRandom();
        byte[] bytes = seedGen.generateSeed(8);
//        String s21= gensalt("$2a", 10, bytes);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String pw= passwordEncoder.encode("admin123");
//        System.out.println(pw);
        String pw1= BCrypt.hashpw("admin123", s);
        System.out.println(pw1);

//        $2a$10$DsKE66pVTu.RbgBarSXq.uErpXYKDRmpSc0GriXXYM9w1R/rbbYO6
    }
}
