package marketplace_la_u.marketplace_la_u.service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import marketplace_la_u.marketplace_la_u.DTO.auth.RegisterRequest;
import marketplace_la_u.marketplace_la_u.DTO.user.UserUpdateRequest;
import marketplace_la_u.marketplace_la_u.model.Carreras;
import marketplace_la_u.marketplace_la_u.model.Universidades;
import marketplace_la_u.marketplace_la_u.model.Users;
import marketplace_la_u.marketplace_la_u.repository.CarrerasRepository;
import marketplace_la_u.marketplace_la_u.repository.UniversidadesRepository;
import marketplace_la_u.marketplace_la_u.repository.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import marketplace_la_u.marketplace_la_u.DTO.user.UserResponse;

import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Service
@Transactional
public class UsersService {

    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final CarrerasRepository careerRepository;
    private final UniversidadesRepository universityRepository;

    public UserResponse registerUser(RegisterRequest userDto){

        if (repository.existsByEmail(userDto.getEmail())){
            throw new IllegalStateException("Correo ya registrado.");
        }

        if (repository.existsByUsername(userDto.getUsername())){
            throw new IllegalStateException("Username ya registrado");
        }

        Users user = new Users();
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setProfession(userDto.getProfession());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Users newUser = repository.save(user);
        return new UserResponse(newUser);
    }

    public List<UserResponse> listUser(){
        return repository.findAll().stream().map(UserResponse::new).toList();
    }

    public void deleteUser(Long id){
        if (!repository.existsById(id)){
            throw new EntityNotFoundException("Usuario no encontrado.");
        }
        repository.deleteById(id);
    }

    public UserResponse updateUser(Long id, UserUpdateRequest nuevosDatos){
        return repository.findById(id).map(user ->{
            if(nuevosDatos.getEmail() != null) {
                if (repository.existsByEmailAndIdNot(nuevosDatos.getEmail(), id)) {
                    throw new IllegalStateException("Email ya en uso.");
                }
                user.setEmail(nuevosDatos.getEmail());
            }

            if(nuevosDatos.getName() != null) user.setName(nuevosDatos.getName());
            if(nuevosDatos.getProfession() != null) user.setProfession(nuevosDatos.getProfession());
            if(nuevosDatos.getAddress() != null) user.setAddress(nuevosDatos.getAddress());

            if(nuevosDatos.getCareerId() != null){
                Carreras career = careerRepository.findById(nuevosDatos.getCareerId()).orElseThrow(() -> new EntityNotFoundException("Carrera no encontrada."));
                user.setCarreras(career);
            }

            if(nuevosDatos.getUniversityId() != null){
                Universidades university = universityRepository.findById(nuevosDatos.getUniversityId()).orElseThrow(() -> new EntityNotFoundException("Universidad no encontrada."));
                user.setUniversidades(university);
            }

            return new UserResponse(repository.save(user));
        }).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado."));
    }

    public UserResponse consultById(Long id){
        return repository.findById(id).map(UserResponse::new).orElseThrow(()-> new EntityNotFoundException("Usuario no encontrado."));
    }
}
