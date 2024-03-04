package com.example.sharddemo.service;

import com.example.sharddemo.configuration.DBSourceEnum;
import com.example.sharddemo.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostSelectorService {

    private final PostService postService;

    public void saveAll(List<PostDto> postSourceOnes) {
        var groupedPosts = postSourceOnes
                .stream()
                .collect(Collectors.groupingBy(
                                p -> selectRepositoryByType(p.getType())
                        )
                );

        var futures = new ArrayList<CompletableFuture<Void>>();
        for (var entry : groupedPosts.entrySet()) {
            futures.add(
                    CompletableFuture.runAsync(
                            () -> postService.saveAll(
                                    entry.getKey(),
                                    entry.getValue()
                            )
                    )
            );
        }
        for (var future : futures) {
            try {
                future.get();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                log.error(exception.getMessage(), exception);
            } catch (Exception exception) {
                log.error(exception.getMessage(), exception);
            }
        }
    }

    private DBSourceEnum selectRepositoryByType(Long type) {
        if (type > 0 && type <= 9) {
            return DBSourceEnum.SOURCE_ONE;
        } else if (type > 10 && type <= 99) {
            return DBSourceEnum.SOURCE_TWO;
        } else {
            return DBSourceEnum.DEFAULT;
        }
    }

}
