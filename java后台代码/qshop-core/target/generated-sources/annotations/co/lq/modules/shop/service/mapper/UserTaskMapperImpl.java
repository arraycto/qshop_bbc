package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.UserTask;
import co.lq.modules.shop.service.dto.UserTaskDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-05-09T22:21:13+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_40 (Oracle Corporation)"
)
@Component
public class UserTaskMapperImpl implements UserTaskMapper {

    @Override
    public UserTask toEntity(UserTaskDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserTask userTask = new UserTask();

        userTask.setId( dto.getId() );
        userTask.setName( dto.getName() );
        userTask.setRealName( dto.getRealName() );
        userTask.setTaskType( dto.getTaskType() );
        userTask.setNumber( dto.getNumber() );
        userTask.setLevelId( dto.getLevelId() );
        userTask.setSort( dto.getSort() );
        userTask.setIsShow( dto.getIsShow() );
        userTask.setIsMust( dto.getIsMust() );
        userTask.setIllustrate( dto.getIllustrate() );
        userTask.setAddTime( dto.getAddTime() );

        return userTask;
    }

    @Override
    public UserTaskDTO toDto(UserTask entity) {
        if ( entity == null ) {
            return null;
        }

        UserTaskDTO userTaskDTO = new UserTaskDTO();

        userTaskDTO.setId( entity.getId() );
        userTaskDTO.setName( entity.getName() );
        userTaskDTO.setRealName( entity.getRealName() );
        userTaskDTO.setTaskType( entity.getTaskType() );
        userTaskDTO.setNumber( entity.getNumber() );
        userTaskDTO.setLevelId( entity.getLevelId() );
        userTaskDTO.setSort( entity.getSort() );
        userTaskDTO.setIsShow( entity.getIsShow() );
        userTaskDTO.setIsMust( entity.getIsMust() );
        userTaskDTO.setIllustrate( entity.getIllustrate() );
        userTaskDTO.setAddTime( entity.getAddTime() );

        return userTaskDTO;
    }

    @Override
    public List<UserTask> toEntity(List<UserTaskDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<UserTask> list = new ArrayList<UserTask>( dtoList.size() );
        for ( UserTaskDTO userTaskDTO : dtoList ) {
            list.add( toEntity( userTaskDTO ) );
        }

        return list;
    }

    @Override
    public List<UserTaskDTO> toDto(List<UserTask> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserTaskDTO> list = new ArrayList<UserTaskDTO>( entityList.size() );
        for ( UserTask userTask : entityList ) {
            list.add( toDto( userTask ) );
        }

        return list;
    }
}
