package pcronos.integracao.fornecedor.entidades;

import java.time.LocalDateTime;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;



//Foi testado que n�o faz nenhuma diferen�a usar "@Length" do Hibernate ou "@Size" do JPA,
//at� os atributos "max" e "message" funcionam igualmente: 
//@Length(max = 15, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
//@Size(max = 15, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
//import org.hibernate.validator.Length;  // hibernate.validator.3.1.0.GA

//N�o funcionou: @Length(max = 15, message = "IdFornecedor = ${ConfigMonitoradorIntegradores.IdFornecedor}: PrenomeContatoTIsecundario = ${validatedValue}, tamanho = ${validatedValue.length()}, max = {max}")
//N�o funcionou: @Length(max = 15, message = "IdFornecedor = ${IdFornecedor}: PrenomeContatoTIsecundario = ${validatedValue}, tamanho = ${validatedValue.length()}, max = {max}")
//N�o funcionou: @Length(max = 15, message = "${propertyPath} = ${validatedValue}, tamanho = ${validatedValue.length()}, max = {max}")

import pcronos.integracao.fornecedor.interfaces.SistemaIntegradoInterface;
import pcronos.integracao.fornecedor.interfaces.FornecedorOuSistemaIntegradoInterface;



@Entity // Para evitar org.hibernate.MappingException: Unknown entity que acontece quando usar class-level constraints com EL
@Table(name="Configuracao_Instalador_Integrador_Nuvem")
public class ConfigInstaladorIntegradorNuvem implements SistemaIntegradoInterface, FornecedorOuSistemaIntegradoInterface {

	public ConfigInstaladorIntegradorNuvem() {}
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_config_instalador_integrador_nuvem_ciintnuv")
	int Id;
	
	@Column(name="id_sistema_integrado_sisint")
	int IdSistemaIntegrado;
	// O seguinte serve apenas para evitar erro "javax.el.PropertyNotFoundException" pelo class-level Hibernate constraint com EL:
	@Override
	public int getIdSistemaIntegrado() { return IdSistemaIntegrado; }
	@Override
	public int getIdFornecedorOuSistemaIntegrado() { return IdSistemaIntegrado; }
	
    @Column(name="tipo_sist_operacional_ciintnuv")
    @Length(max=30, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    String TipoSO;
    
    @Column(name="sist_operacional_32_ou_64_bit_ciintnuv")
    @Length(max=6, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    String So32ou64bit;
    
    @Column(name="idioma_sist_operacional_ciintnuv")
    @Length(max=1, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    String IdiomaSO;
    
    @Column(name="espaco_livre_disco_ciintnuv")
    @Length(max=10, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    String EspacoLivreDisco;
    
    @Column(name="memoria_ram_livre_ciintnuv")
    @Length(max=10, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    String MemoriaRamLivre;
    
    @Column(name="versao_jre_ciintnuv")
    @Length(max=15, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    String VersaoJRE;
    
    @Column(name="tipo_jre_ciintnuv")
    @Length(max=10, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    String TipoJRE;
    
    @Column(name="versao_integrador_ciintnuv")
    @Length(max=10, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    String VersaoIntegrador;
    
    @Column(name="disco_integrador_ciintnuv")
    @Length(max=1, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    String DiscoIntegrador;
    
    @Column(name="dir_programfiles_ciintnuv")
    @Length(max=30, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    String DirProgramfiles;
    
    @Column(name="endereco_ip_publico_servidor_ciintnuv")
    @Length(max=30, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    String EnderecoIpPublicoServidor;
    
    @Column(name="porta_ip_aberta_ciintnuv")
    @Length(max=15, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    String PortaIpAberta;
    
    @Column(name="frequencia_processamento_ciintnuv")
    @Length(max=10, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    String FrequenciaProcessamento;
    

    
	@Column(name="dt_cadastro_ciintnuv")
	public LocalDate DtCadastro;
 	
	@Column(name="dt_desativacao_ciintnuv")
 	LocalDateTime DtDesativacao;
 	
	@Column(name="dt_alteracao_ciintnuv")
 	LocalDateTime DtAlteracao;
 	
	@Column(name="user_id_ususis")
	public int IdUsuario;
}
