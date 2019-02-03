package model.entities;

import java.util.Date;

import model.enums.Departamento;
import model.enums.Cargo;

public class Funcionario {

	private Departamento departamento;
	private Cargo cargo;
	private Integer matricula;
	private String nomeCompleto;
	private Date admissao;
	private Integer dependentes;
	private Double salarioBruto;

	public Funcionario(Departamento departamento, Cargo cargo, Integer matricula, String nomeCompleto, Date admissao,
			Integer dependentes, Double salarioBruto) {
		this.departamento = departamento;
		this.cargo = cargo;
		this.matricula = matricula;
		this.nomeCompleto = nomeCompleto;
		this.admissao = admissao;
		this.dependentes = dependentes;
		this.salarioBruto = salarioBruto;
	}
	
	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	public Integer getMatricula() {
		return matricula;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public Date getAdmissao() {
		return admissao;
	}

	public Integer getDependentes() {
		return dependentes;
	}

	public void setDependentes(Integer dependentes) {
		this.dependentes = dependentes;
	}

	public Double getSalarioBruto() {
		return salarioBruto;
	}

	public Double calcularINSS() {
		double[] inss = new double[3];
		inss[0] = 0.08;
		inss[1] = 0.09;
		inss[2] = 0.11;

		if (getSalarioBruto() <= 1751.81) {
			return getSalarioBruto() * inss[0];
		} else if (getSalarioBruto() >= 1751.82 && getSalarioBruto() <= 2919.72) {
			return getSalarioBruto() * inss[1];
		} else if (getSalarioBruto() >= 2919.73 && getSalarioBruto() <= 5839.45) {
			return getSalarioBruto() * inss[2];
		} else {
			return 5839.45 * inss[2];
		}
	}

	public Double calcularIR() {
		double[][] irpf = new double[2][5];
		// alíquotas de IRPF 2018
		irpf[0][0] = 0;
		irpf[0][1] = 0.075;
		irpf[0][2] = 0.15;
		irpf[0][3] = 0.225;
		irpf[0][4] = 0.275;

		// parcelas deducao IRPF 2018
		irpf[1][0] = 0;
		irpf[1][0] = 142.80;
		irpf[1][0] = 354.80;
		irpf[1][0] = 636.13;
		irpf[1][0] = 869.36;

		// deducao por denpendente 2018
		Double valorDep = 189.59;

		Double baseIR = (getSalarioBruto() - calcularINSS() - (getDependentes() * valorDep));
		Double totalIR = 0.0;

		if (baseIR <= 1903.98) { // 1 FAIXA (até 1.903,98) - sem desconto
			totalIR = 0.0;
		} else if (baseIR >= 1903.99 && baseIR <= 2826.65) { // 2 FAIXA (1.903,99 até 2.826,65) - 7,5% - 142,80
			totalIR = 0.0;// valor do cálculo da faixa 1
			totalIR += (((baseIR - 1903.99) * irpf[0][1]) - irpf[1][1]);
		} else if (baseIR >= 2826.66 && baseIR <= 3751.05) { // 3 FAIXA (2.826,66 até 3.751,05) - 15% - 354,80
			totalIR = 0.0;
			totalIR += ((2826.65 - 1903.99) * irpf[0][1]) - irpf[1][1];
			totalIR += ((baseIR - 2826.66) * irpf[0][2]) - irpf[1][2];
		} else if (baseIR >= 3751.06 && baseIR <= 4664.68) { // 4 FAIXA (3.751,06 até 4.664,68) - 22,5% - 636,13
			totalIR = 0.0;
			totalIR += ((2826.65 - 1903.99) * irpf[0][1]) - irpf[1][1];
			totalIR += ((3751.05 - 2826.66) * irpf[0][2]) - irpf[1][2];
			totalIR += ((baseIR - 3751.06) * irpf[0][3]) - irpf[1][3];
		} else if (baseIR >= 4664.69) { // 5 FAIXA (Acima de 4.664,68) - 27,5% - 869,36
			totalIR = 0.0;
			totalIR += ((2826.65 - 1903.99) * irpf[0][1]) - irpf[1][1];
			totalIR += ((3751.05 - 2826.66) * irpf[0][2]) - irpf[1][2];
			totalIR += ((4664.68 - 3751.06) * irpf[0][3]) - irpf[1][3];
			totalIR += ((baseIR - 4664.69) * irpf[0][4]) - irpf[1][4];
		} else {
			System.out.println("Algum erro aconteceu. Verificar");
		}

		return totalIR;
	}

	public Double calcularFGTS() {
		return getSalarioBruto() * 0.08;
	}

	public Double calcularSalarioLiquido() {
		return getSalarioBruto() - calcularINSS() - calcularIR();
	}
	
	@Override
	public String toString() {
		return getMatricula() + "-" + getNomeCompleto() 
				+ " (" + getCargo() + " / " + getDepartamento() + ")"
				+ "\nDependentes: " + getDependentes()
				+ "\n----------------------------------------------------------------"
				+ "\nSALÁRIO BRUTO: R$ " + String.format("%.2f", getSalarioBruto())
				+ "\n(-) Desc INSS: R$ " + String.format("%.2f", calcularINSS())
				+ "\n(-) Desc IRRF: R$ " + String.format("%.2f", calcularIR())
				+ "\nSALÁRIO LÍQUIDO: R$ " + String.format("%.2f", calcularSalarioLiquido())
				+ "\n---> FGTS do mês: R$ " + String.format("%.2f", calcularFGTS())
				+ "\n================================================================";
	}
	
}
