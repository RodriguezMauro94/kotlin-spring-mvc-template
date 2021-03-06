package api.users

import api.logger
import io.github.cdimascio.japierrors.ApiError.notFound
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.NotNull

@RestController
@Validated
@RequestMapping("/users")
class UsersController(private val usersService: UsersService) {

    @GetMapping
    fun all() = usersService.all()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @NotNull @RequestBody user: UserPostBody
    ): User {
        logger.info("Create user $user")
        return usersService.create(user)
    }

    @GetMapping("/{id}")
    fun byId(
        @NotNull @PathVariable(value = "id") id: Long
    ) = usersService.byId(id) ?: throw notFound()

    @PutMapping("/{id}")
    fun update(
        @NotNull @PathVariable(value = "id") id: Long,
        @NotNull @RequestBody changes: UserPutBody
    ): User {
        logger.info("Update user $id with $changes")
        return usersService.update(
            changes.copy(id = id)
        ) ?: throw notFound()
    }

    @DeleteMapping("/{id}")
    fun delete(
        @NotNull @PathVariable(value = "id") id: Long
    ): User {
        logger.info("Delete user $id")
        return usersService.delete(id) ?: throw notFound()
    }
}
