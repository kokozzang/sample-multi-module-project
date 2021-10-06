package com.idolpark.batch.order.job;

import com.google.common.collect.Iterables;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.AbstractPagingItemReader;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

@Slf4j
public class RepositoryMethodReader<T, K> extends AbstractPagingItemReader<T> {

    /**
     * 마지막 offset key 를 객체 에서 꺼내기 위한 Function
     * 기준 컬럼의 메소드
     */
    private final Function<T, K> findLastOffsetFunction;

    /**
     * DB나 기타 저장소에서 조회하는 비즈니스 로직 Function
     * repository 메소드
     */
    private final Function<K, Collection<T>> readerFunction;


    /**
     * 조회한 마지막 offset. null 일수도 있다. 일부 API는 null 로 넣어야 올바로 작동하 술 있다(정렬기준 desc 인 경우)
     */
    private K lastOffset;


    @Builder
    public RepositoryMethodReader(Function<T, K> findLastOffsetFunction,
                                  Function<K, Collection<T>> readerFunction,
                                  K initialOffset) {
        this.findLastOffsetFunction = findLastOffsetFunction;
        this.readerFunction = readerFunction;

        this.lastOffset = initialOffset;
    }

    @Override
    protected void doReadPage() {
        if (results == null) {
            results = new CopyOnWriteArrayList<>();
        } else {
            results.clear();
        }


        Collection<T> pageData = readerFunction.apply(lastOffset);
        log.info("[READER] read page data. lastIndex: [{}], size: [{}]", lastOffset, pageData.size());

        if (!pageData.isEmpty()) {
            T last = Iterables.getLast(pageData);
            lastOffset = findLastOffsetFunction.apply(last);
        }

        results.addAll(pageData);

    }

    @Override
    protected void doJumpToPage(int itemIndex) {
        throw new UnsupportedOperationException();
    }
}
