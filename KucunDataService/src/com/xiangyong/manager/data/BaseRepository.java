package com.xiangyong.manager.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by yuxiangyong on 2017/9/17.
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Number> extends JpaRepository<T,ID> {
}
