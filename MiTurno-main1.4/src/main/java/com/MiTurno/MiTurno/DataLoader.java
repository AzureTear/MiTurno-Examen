package com.MiTurno.MiTurno;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.MiTurno.MiTurno.model.Configuracion;
import com.MiTurno.MiTurno.model.Estado;
import com.MiTurno.MiTurno.model.EstadoModulo;
import com.MiTurno.MiTurno.model.Institucion;
import com.MiTurno.MiTurno.model.Modulo;
import com.MiTurno.MiTurno.model.Rol;
import com.MiTurno.MiTurno.model.Sucursal;
import com.MiTurno.MiTurno.model.Trabajador;
import com.MiTurno.MiTurno.model.Turno;
import com.MiTurno.MiTurno.model.Usuario;
import com.MiTurno.MiTurno.repository.ConfiguracionRepository;
import com.MiTurno.MiTurno.repository.EstadoModuloRepository;
import com.MiTurno.MiTurno.repository.EstadoRepository;
import com.MiTurno.MiTurno.repository.InstitucionRepository;
import com.MiTurno.MiTurno.repository.ModuloRepository;
import com.MiTurno.MiTurno.repository.RolRepository;
import com.MiTurno.MiTurno.repository.SucursalRepository;
import com.MiTurno.MiTurno.repository.TrabajadorRepository;
import com.MiTurno.MiTurno.repository.TurnoRepository;
import com.MiTurno.MiTurno.repository.UsuarioRepository;

import java.util.Date;
import java.util.List;
import java.util.Random;


@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private ConfiguracionRepository configuracionRepository;

    @Autowired
    private InstitucionRepository institucionRepository;

    @Autowired
    private ModuloRepository moduloRepository;

    @Autowired
    private EstadoModuloRepository estadoModuloRepository;
    
    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private RolRepository rolRepository;
        
    @Autowired
    private SucursalRepository sucursalRepository;    

    @Autowired
    private TrabajadorRepository trabajadorRepository;
    
    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
        
        //generar Configuracion
        for (int i = 0; i < 3; i++) {
            Configuracion configuracion = new Configuracion();
            configuracion.setId(i + 1);
            configuracion.setNotificacionesActivadas(random.nextBoolean());
            configuracion.setTiempo_anticipacion_notificacion(faker.number().numberBetween(660000000, 55555555));
            configuracionRepository.save(configuracion);
        }
        //generar Institucion
            for (int i = 0; i < 5; i++){
            Institucion institucion = new Institucion();
            institucion.setId(i+1);
            institucion.setNombre(faker.company().name());
            institucion.setEmail(faker.internet().emailAddress());
            institucion.setTelefono(faker.number().numberBetween(100000000, 999999999));
            institucionRepository.save(institucion);
        }
        //generar EstadoModulo
        for(int i = 0; i < 5; i++){
            EstadoModulo estadoModulo = new EstadoModulo();
            estadoModulo.setId(i + 1);
            estadoModuloRepository.save(estadoModulo);
        }
        List<EstadoModulo> estadoModulo = estadoModuloRepository.findAll();
        //generar Modulo
        for(int i = 0; i < 5; i++){
            Modulo modulo = new Modulo();
            modulo.setId(i + 1);
            modulo.setTitulo(faker.options().option("Modulo A", "Modulo B"));
            modulo.setEstado(faker.options().option("Bueno", "Malo"));
            modulo.setEstadoModulo(estadoModulo.get(random.nextInt(estadoModulo.size())));
            moduloRepository.save(modulo);
        }
        //generar Estado 
        for(int i = 0; i < 2; i++){
            Estado estado = new Estado();
            estado.setId(i + 1);
            estado.setDescripcionEstado(faker.options().option("bueno", "malo"));
            estadoRepository.save(estado);
        } 
        //generar Usuario
        for(int i=0; i < 5; i++){
            Usuario usuario =new Usuario();
            usuario.setId(i+1);
            usuario.setNombre(faker.name().firstName());
            usuario.setApellido(faker.name().lastName());
            usuario.setRut(faker.idNumber().valid());
            usuario.setTelefono(faker.number().numberBetween(10000000, 999999999));
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setContraseña(faker.internet().password());
            usuarioRepository.save(usuario);
        }
        List<Configuracion> configuracion = configuracionRepository.findAll();
        List<Institucion> institucion = institucionRepository.findAll();
        //generar Sucursal
        for(int i=0;i<5;i++){
            Sucursal sucursal = new Sucursal();
            sucursal.setId(i+1);
            sucursal.setDireccion(faker.location().toString());
            sucursal.setTelefono(faker.number().numberBetween(100000000, 999999999));
            sucursal.setHorario(new Date());
            sucursal.setCapacidadMaxima(faker.number().numberBetween(10, 999));
            sucursal.setConfiguracion(configuracion.get(random.nextInt(configuracion.size())));
            sucursal.setInstitucion(institucion.get(random.nextInt(institucion.size())));
            sucursalRepository.save(sucursal);
        }
        List<Sucursal> sucursal = sucursalRepository.findAll();
        List<Estado> estado = estadoRepository.findAll(); 
        List<Usuario> usuario = usuarioRepository.findAll();
        List<Modulo> modulo = moduloRepository.findAll();        
        //generar Turno
        for(int i = 0; i < 5; i++){
            Turno turno = new Turno();
            turno.setId(i+ 1);
            turno.setFecha(new Date());
            turno.setNumero(faker.number().numberBetween(100000000, 999999999));
            turno.setHoraCreacion(new Date());
            turno.setHoraAtencion(new Date());
            turno.setHoraCancelacion(new Date(System.currentTimeMillis()+faker.number().numberBetween(3600000, 7200000)));
            turno.setSucursal(sucursal.get(random.nextInt(sucursal.size())));
            turno.setEstado(estado.get(random.nextInt(estado.size())));
            turno.setUsuario(usuario.get(random.nextInt(usuario.size())));
            turno.setModulo(modulo.get(random.nextInt(modulo.size())));            
            turnoRepository.save(turno);
        }
        //generar rol
        for(int i =0; i <3; i ++){
            Rol rol = new Rol();
            rol.setId(i+1);
            rol.setDescripcion(faker.options().option("admin","usuario"));
            rol.setAdmin(random.nextBoolean());
            rolRepository.save(rol);
        }
        List<Rol> rol = rolRepository.findAll(); 
        //generar trabajdor
        for(int i =0; i< 5; i ++){
            Trabajador trabajador = new Trabajador();
            trabajador.setId(i+1);
            trabajador.setNombre (faker.name().firstName());
            trabajador.setSegundoNombre (faker.name().firstName());
            trabajador.setApellido(faker.name().lastName());
            trabajador.setSucursal(sucursal.get(random.nextInt(sucursal.size())));
            trabajador.setRol(rol.get(random.nextInt(rol.size())));
            trabajador.setModulo(modulo.get(random.nextInt(modulo.size())));
            trabajadorRepository.save(trabajador);
        }
    }   

}