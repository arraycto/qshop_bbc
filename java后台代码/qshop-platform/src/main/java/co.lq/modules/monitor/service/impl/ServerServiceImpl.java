package co.lq.modules.monitor.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.hutool.http.HttpUtil;
import co.lq.modules.monitor.domain.Server;
import co.lq.modules.monitor.repository.ServerRepository;
import co.lq.modules.monitor.service.ServerService;
import co.lq.modules.monitor.service.dto.ServerDTO;
import co.lq.modules.monitor.service.dto.ServerQueryCriteria;
import co.lq.modules.monitor.service.mapper.ServerMapper;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author Zhang houying
 * @date 2019-11-03
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    private final ServerMapper     serverMapper;

    public ServerServiceImpl(ServerRepository serverRepository, ServerMapper serverMapper) {
        this.serverRepository = serverRepository;
        this.serverMapper = serverMapper;
    }

    @Override
    public Map<String, Object> queryAll(ServerQueryCriteria criteria, Pageable pageable) {
        Page<Server> page = serverRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        page.forEach(server -> {
            try {
                server.setState("1");
                String url = String.format("http://%s:%d/api/serverMonitor", server.getAddress(), server.getPort());
                String res = HttpUtil.get(url, 1000);
                JSONObject obj = JSONObject.parseObject(res);
                server.setCpuRate(obj.getDouble("cpuRate"));
                server.setCpuCore(obj.getInteger("cpuCore"));
                server.setMemTotal(obj.getDouble("memTotal"));
                server.setMemUsed(obj.getDouble("memUsed"));
                server.setDiskTotal(obj.getDouble("diskTotal"));
                server.setDiskUsed(obj.getDouble("diskUsed"));
                server.setSwapTotal(obj.getDouble("swapTotal"));
                server.setSwapUsed(obj.getDouble("swapUsed"));
            } catch (Exception e) {
                server.setState("0");
                e.printStackTrace();
            }
        });

        return PageUtil.toPage(page.map(serverMapper::toDto));
    }

    @Override
    public List<ServerDTO> queryAll(ServerQueryCriteria criteria) {
        return serverMapper.toDto(serverRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public ServerDTO findById(Integer id) {
        Server server = serverRepository.findById(id).orElseGet(Server::new);
        ValidationUtil.isNull(server.getId(), "Server", "id", id);
        return serverMapper.toDto(server);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerDTO create(Server resources) {
        return serverMapper.toDto(serverRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Server resources) {
        Server server = serverRepository.findById(resources.getId()).orElseGet(Server::new);
        ValidationUtil.isNull(server.getId(), "Server", "id", resources.getId());
        server.copy(resources);
        serverRepository.save(server);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        for (Integer id : ids) {
            serverRepository.deleteById(id);
        }
    }

}
