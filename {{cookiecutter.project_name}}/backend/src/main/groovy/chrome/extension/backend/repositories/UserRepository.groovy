package chrome.extension.backend.repositories

import chrome.extension.backend.models.User
import groovy.transform.CompileStatic
import org.springframework.data.mongodb.repository.MongoRepository

@CompileStatic
interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email)

    User findByUsername(String username)

    boolean existsByEmail(String email)

    boolean existsByUsername(String username)

}