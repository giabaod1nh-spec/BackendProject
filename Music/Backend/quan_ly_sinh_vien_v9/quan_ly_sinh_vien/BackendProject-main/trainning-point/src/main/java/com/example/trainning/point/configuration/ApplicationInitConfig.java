package com.example.trainning.point.configuration;

import com.example.trainning.point.dto.request.RoleRequest;
import com.example.trainning.point.entity.ClassManager;
import com.example.trainning.point.entity.Role;
import com.example.trainning.point.entity.Semester;
import com.example.trainning.point.entity.User;
import com.example.trainning.point.enums.RoleEnum;
import com.example.trainning.point.repository.IClassManagerRepository;
import com.example.trainning.point.repository.ISemesterRepository;
import com.example.trainning.point.repository.RoleRepository;
import com.example.trainning.point.repository.UserRepository;
import com.example.trainning.point.service.impl.RoleService;
import com.example.trainning.point.service.interfaces.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//Tao auto 1 tk admin
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    RoleService roleService;
    IUserService userService;
    UserRepository userRepository;
    IClassManagerRepository classManagerRepository;
    ISemesterRepository semesterRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            this.createSemester();
            this.createDefaultRoles();
            this.createClass();
            this.createAnyStudent();

            if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
//             var roles = new HashSet<String>();
//             roleRepository.findById(com.example.trainning.point.enums.Role.ADMIN.name());
//             roles.add(com.example.trainning.point.enums.Role.ADMIN.name());

                Set<Role> roles = new HashSet<>();
                Role role = Role.builder()
                        .name(RoleEnum.ADMIN.name())
                        .permissions(null)
                        .build();
                roles.add(role);

                User user = User.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("1234")) //can them passwordencode -> tao 1 bean rieng de su dung
                        //.active(true)
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("Admin user has been created with default password: admin");
            }
        };
    }

    //---create roles default
    private void createDefaultRoles() {
        List<String> roles = Arrays.stream(RoleEnum.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        for (var it : roles) {
            if (roleService.findByName(it) == null) {
                RoleRequest request = new RoleRequest();
                request.setName(it);
                request.setPermissions(new HashSet<>());

                roleService.create(request);
            }
        }
    }

    private void createAnyStudent() {
        ClassManager classManager = classManagerRepository.findById(1L).orElse(null);
        if (userService.count() < 10) {
            Role role = Role.builder()
                    .name(RoleEnum.STUDENT.name())
                    .permissions(null)
                    .build();

            Role roleConselor = Role.builder()
                    .name(RoleEnum.COUNSELOR.name())
                    .permissions(null)
                    .build();

            Role roleLecture = Role.builder()
                    .name(RoleEnum.LECTURER.name())
                    .permissions(null)
                    .build();

            Role roleCommittee = Role.builder()
                    .name(RoleEnum.COMMITTEE.name())
                    .permissions(null)
                    .build();
            Set<Role> roles = new HashSet<>();
            roles.add(role);

            List<User> users = List.of(
                    new User("sv01", "minh01@gmail.com", passwordEncoder.encode("1234"), "Nguyễn Văn Minh"),
                    new User("sv02", "linh02@gmail.com", passwordEncoder.encode("1234"), "Trần Thị Linh"),
                    new User("sv03", "phuc03@gmail.com", passwordEncoder.encode("1234"), "Phạm Đức Phúc"),
                    new User("sv04", "thu04@gmail.com", passwordEncoder.encode("1234"), "Lê Thu Hà"),
                    new User("sv05", "khoa05@gmail.com", passwordEncoder.encode("1234"), "Vũ Minh Khoa"),
                    new User("sv06", "nga06@gmail.com", passwordEncoder.encode("1234"), "Đỗ Thị Nga"),
                    new User("sv07", "duy07@gmail.com", passwordEncoder.encode("1234"), "Ngô Duy Anh"),
                    new User("sv08", "hoa08@gmail.com", passwordEncoder.encode("1234"), "Nguyễn Hồng Hoa"),
                    new User("sv09", "tuan09@gmail.com", passwordEncoder.encode("1234"), "Bùi Văn Tuấn"),
                    new User("sv010", "yen10@gmail.com", passwordEncoder.encode("1234"), "Hoàng Thị Yến")
            );

            for (var it: users){
                it.setRoles(roles);
                it.setClassManager(classManager);
                userRepository.save(it);
            }

            User conselor = new User("cv01", "bao11@gmail.com", passwordEncoder.encode("1234"), "Trịnh Hoàng Bảo");
            User lecture = new User("gv01", "hanh12@gmail.com", passwordEncoder.encode("1234"), "Lương Mỹ Hạnh");
            User committee = new User("lt01", "minh14@gmail.com", passwordEncoder.encode("1234"), "Phạm Minh Nhật");

            conselor.setRoles(Set.of(roleConselor));
            conselor.setClassManager(classManager);
            userRepository.save(conselor);

            lecture.setRoles(Set.of(roleLecture));
            lecture.setClassManager(classManager);
            userRepository.save(lecture);

            committee.setRoles(Set.of(roleCommittee));
            committee.setClassManager(classManager);
            userRepository.save(committee);
        }
    }

    private void createClass(){
        if (classManagerRepository.count() == 0){
            ClassManager classManager = ClassManager.builder()
                    .codeClass("SE01")
                    .name("Ky thuat phan mem 01")
                    .academicCohort("K20")
                    .startDate(LocalDate.of(2025, 6, 1))
                    .endDate(LocalDate.of(2021, 9, 1))
                    .build();

            classManagerRepository.save(classManager);
        }
    }

    private void createSemester(){
        if(semesterRepository.count() == 0) {
            Semester semester = Semester.builder()
                    .name("Ky 1")
                    .startDate(LocalDate.of(2021, 1, 1))
                    .endDate(LocalDate.of(2025, 12, 31))
                    .build();

            semesterRepository.save(semester);
        }
    }

}
