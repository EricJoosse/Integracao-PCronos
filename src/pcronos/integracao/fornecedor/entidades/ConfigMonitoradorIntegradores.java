package pcronos.integracao.fornecedor.entidades;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


// Foi testado que n�o faz nenhuma diferen�a usar "@Length" do Hibernate ou "@Size" do JPA,
// at� os atributos "max" e "message" funcionam igualmente: 
// @Length(max = 15, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
// @Size(max = 15, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
//import org.hibernate.validator.Length;  // hibernate.validator.3.1.0.GA

// N�o funcionou: @Length(max = 15, message = "IdFornecedor = ${ConfigMonitoradorIntegradores.IdFornecedor}: PrenomeContatoTIsecundario = ${validatedValue}, tamanho = ${validatedValue.length()}, max = {max}")
// N�o funcionou: @Length(max = 15, message = "IdFornecedor = ${IdFornecedor}: PrenomeContatoTIsecundario = ${validatedValue}, tamanho = ${validatedValue.length()}, max = {max}")
// N�o funcionou: @Length(max = 15, message = "${propertyPath} = ${validatedValue}, tamanho = ${validatedValue.length()}, max = {max}")


import pcronos.integracao.fornecedor.annotations.ValidConfigMonitoradorIntegradores;
import pcronos.integracao.fornecedor.interfaces.FornecedorInterface;



@Entity // Para evitar org.hibernate.MappingException: Unknown entity que acontece quando usar class-level constraints com EL
@Table(name="Configuracao_Monitorador_Integradores")
// O seguinte d� erro javax.el.PropertyNotFoundException
//@ValidConfigMonitoradorIntegradores( message = "Fornecedor ${validatedValue.IdFornecedor} inv�lido")
@ValidConfigMonitoradorIntegradores( message = "IdFornecedor = ${validatedValue.getIdFornecedor()}: ${validatedValue}: Fornecedor inv�lido")
// N�o funciona:
//@ValidConfigMonitoradorIntegradores( message = "Entidade ${validatedValue.toString().replace(\"cronos.integracao.fornecedor.entidades\",\"\"}: Fornecedor ${validatedValue.getIdFornecedor()} inv�lido")
//@ValidConfigMonitoradorIntegradores( message = "Entidade ${validatedValue.getRootBeanClass().getSimpleName()}: Fornecedor ${validatedValue.getIdFornecedor()} inv�lido")
public class ConfigMonitoradorIntegradores implements FornecedorInterface {

	public ConfigMonitoradorIntegradores() {}
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_config_monitorador_integradores_conmonint")
	public int Id;
	
	@Column(name="id_fornecedor_fornec")
	public int IdFornecedor;
	// O seguinte serve apenas para evitar erro "javax.el.PropertyNotFoundException" pelo class-level Hibernate constraint com EL:
	@Override
	public int getIdFornecedor() { return IdFornecedor; }
	
	
	@Column(name="id_vendedor_responsavel_integracao_ususis")
	public int IdVendedorResponsavel;
	
	@Column(name="sn_em_producao_conmonint")
	public boolean IsEmProducao;

    @Column(name="id_contato_TI_integrador_contiint")
    public int IdContatoTiIntegrador;

    @Column(name="id_contato_TI_secundario_integrador_contiint")
    public Integer IdContatoTiSecundarioIntegrador;

	@Column(name="aplicativo_desktop_remoto_conmonint")
    @Size(max=30, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
	public String AplicativoDesktopRemoto;
	
	@Column(name="id_aplicativo_desktop_remoto_conmonint")
    @Size(max=30, message = " = ${validatedValue}, tamanho = ${validatedValue.length()}, m�ximo permitido = {max}")
	public String IdAplicativoDesktopRemoto;
	
	@Column(name="dt_cadastro_conmonint")
	public LocalDateTime DtCadastro;
 	
	@Column(name="dt_desativacao_conmonint")
	public LocalDateTime DtDesativacao;
 	
	@Column(name="dt_alteracao_conmonint")
	public LocalDateTime DtAlteracao;
 	
	@Column(name="user_id_ususis")
	public int IdUsuario;
}
