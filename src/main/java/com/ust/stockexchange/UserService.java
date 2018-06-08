
package com.ust.stockexchange;

import com.ust.command.RegistrationReq;
import com.ustack.common.services.ServiceEndpoint;
import java.util.concurrent.CompletableFuture;

@ServiceEndpoint
public interface UserService {
    CompletableFuture<String> registerUser(RegistrationReq req);
}
