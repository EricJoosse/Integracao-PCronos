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

import pcronos.integracao.fornecedor.interfaces.FornecedorInterface;
import pcronos.integracao.fornecedor.interfaces.FornecedorOuSistemaIntegradoInterface;


@Entity // Para evitar org.hibernate.MappingException: Unknown entity que acontece quando usar class-level constraints com EL
@Table(name="Configuracao_Instalador_Integrador")
public class ConfigInstaladorIntegrador implements FornecedorInterface, FornecedorOuSistemaIntegradoInterface {

	public ConfigInstaladorIntegrador() {}
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_config_instalador_integrador_coninsint")
	int Id;
	
	@Column(name="id_fornecedor_fornec")
	public int IdFornecedor;
	// O seguinte serve apenas para evitar erro "javax.el.PropertyNotFoundException" pelo class-level Hibernate constraint com EL:
	@Override
	public int getIdFornecedor() { return IdFornecedor; }
	@Override
	public int getIdFornecedorOuSistemaIntegrado() { return IdFornecedor; }
	
	@Column(name="id_sistema_integrado_sisint")
	public int IdSistemaIntegrado;
	
    @Column(name="usuario_webservice_coninsint")
    @Length(max=30, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    public String UsuarioWebservice;
    
	@Column(name="sn_debug_ativado_coninsint")
	public boolean IsDebugAtivado;

	@Column(name="qtd_dias_arquivos_xml_guardados_coninsint")
	int QtdDiasArquivosXmlGuardados;

	@Column(name="id_config_instalador_integrador_nuvem_ciintnuv")
	public int IdConfigInstaladorIntegradorNuvem;
	
	@Column(name="nr_sequencia_instancia_integrador_nuvem_coninsint")
	public int NrSequenciaInstanciaIntegradorNuvem;
	
    @Column(name="tipo_sist_operacional_coninsint")
    @Length(max=30, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    public String TipoSO;
    
    @Column(name="sist_operacional_32_ou_64_bit_coninsint")
    @Length(max=6, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    public String So32ou64bit;
    
    @Column(name="idioma_sist_operacional_coninsint")
    @Length(max=1, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    String IdiomaSO;
    
    @Column(name="espaco_livre_disco_coninsint")
    @Length(max=10, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    public String EspacoLivreDisco;
    
    @Column(name="memoria_ram_livre_coninsint")
    @Length(max=10, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    public String MemoriaRamLivre;
    
    @Column(name="versao_jre_coninsint")
    @Length(max=15, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    public String VersaoJRE;
    
    @Column(name="tipo_jre_coninsint")
    @Length(max=10, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    public String TipoJRE;
    
    @Column(name="versao_integrador_coninsint")
    @Length(max=10, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    public String VersaoIntegrador;
    
    @Column(name="disco_integrador_coninsint")
    @Length(max=1, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    public String DiscoIntegrador;
    
    @Column(name="dir_programfiles_coninsint")
    @Length(max=30, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    public String DirProgramfiles;
    
    @Column(name="endereco_ip_publico_servidor_coninsint")
    @Length(max=30, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    public String EnderecoIpPublicoServidor;
    
    @Column(name="porta_ip_aberta_coninsint")
    @Length(max=15, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    public String PortaIpAberta;
    
    @Column(name="frequencia_processamento_coninsint")
    @Length(max=10, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
    public String FrequenciaProcessamento;
    
	@Column(name="dt_cadastro_coninsint")
	public LocalDate DtCadastro;
 	
	@Column(name="dt_desativacao_coninsint")
 	LocalDateTime DtDesativacao;
 	
	@Column(name="dt_alteracao_coninsint")
 	LocalDateTime DtAlteracao;
 	
	@Column(name="user_id_ususis")
	public int IdUsuario;
}
