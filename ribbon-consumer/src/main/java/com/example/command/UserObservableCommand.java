package com.example.command;

import com.example.entity.User;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

/**
 * 响应式命令 同 `UserServiceImpl` 响应式
 * @author liushun
 * @since JDK 1.8
 **/
public class UserObservableCommand extends HystrixObservableCommand<User> {
    private RestTemplate restTemplate;
    private Long id;

    public UserObservableCommand(Setter setter, RestTemplate restTemplate, Long id) {
        super(setter);
        this.restTemplate = restTemplate;
        this.id = id;
    }

    /**
     * Implement this method with code to be executed when {@link #observe()} or {@link #toObservable()} are invoked.
     * @return R response type
     */
    @Override
    protected Observable<User> construct() {
        return Observable.unsafeCreate(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                try {
                    if(!subscriber.isUnsubscribed()) {
                        User user = restTemplate.getForObject("http://hello-server/users/{1}", User.class, id);
                        subscriber.onNext(user);
                        subscriber.onCompleted();
                    }
                } catch(Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
