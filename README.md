## [REST API](http://localhost:8080/doc)

## Concept:

- Spring Modulith
    - [Introduction to Spring Modulith](https://www.baeldung.com/spring-modulith)
    - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
    - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: CodeGymJira
```

- There are two tables, which do not have foreign keys
    - _Reference_ - directory. Make the link using _code_ (using id is not allowed, as id is tied to the environment-specific base)
    - _UserBelong_ - link users with type (owner, lead, ...) to object (task, project, sprint, ...). FK will be checked manually

## Analogues

- https://java-source.net/open-source/issue-trackers

## Testing

- https://www.youtube.com/watch?v=aEW8ZH6wj2o

List of completed tasks:
...