package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.entities.Funcionario;
import model.enums.Departamento;
import model.enums.Cargo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CalculaFolha {

	public static void main(String[] args) throws ParseException, IOException {

		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Double somaSalarioBruto = 0.0, somaINSS = 0.0, somaIR = 0.0, somaFGTS = 0.0, somaSalarioLiquido = 0.0;

		System.out.println("- - - CADASTRO DE FUNCIONARIOS - - -");
		System.out.print("Quantos funcionarios serao cadastrados? ");
		int n = sc.nextInt();

		List<Funcionario> lista = new ArrayList<>();

		for (int i = 1; i <= n; i++) {
			// TESTE
			System.out.println("CADASTRO #" + i + ":");
			sc.nextLine();
			System.out.print("Departamento: ");
			String departamento = sc.nextLine();
			System.out.print("Cargo: ");
			String cargo = sc.nextLine();
			System.out.print("Matricula: ");
			Integer matricula = sc.nextInt();
			sc.nextLine();
			System.out.print("Nome: ");
			String nomeCompleto = sc.nextLine();
			System.out.print("Admissao: ");
			Date admissao = sdf.parse(sc.next());
			System.out.print("Salario Bruto R$: ");
			Double salarioBruto = sc.nextDouble();
			System.out.print("Numero de dependentes: ");
			Integer dependentes = sc.nextInt();

			lista.add(new Funcionario(Departamento.valueOf(departamento), Cargo.valueOf(cargo), matricula, nomeCompleto,
					admissao, dependentes, salarioBruto));
		}

		int opt = 0;

		do {

			System.out.println();
			System.out.println("---------------------------------------------------------------------");
			System.out.println("                     SITEMA DE GESTAO DE FOLHA                       ");
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Digite uma das opcoes a seguir: ");
			System.out.println("(1) Relacao de Funcionarios");
			System.out.println("(2) Calcular folha e mostrar resumo por funcionario");
			System.out.println("(3) Calcular folha e mostrar valores totais");
			System.out.println("(-1) Sair");

			opt = sc.nextInt();

			switch (opt) {
			case (1):
				System.out.println("------------ RELACAO DE FUNCIONARIOS ------------");
				for (Funcionario relacao : lista) {
					System.out.println("(" + relacao.getMatricula() + ") " + relacao.getNomeCompleto());
				}
				break;

			case (2):
				for (Funcionario relacao : lista) {
					System.out.println(relacao);
				}
				break;

			case (3):
				for (Funcionario relacao : lista) {
					somaSalarioBruto += relacao.getSalarioBruto();
					somaINSS += relacao.calcularINSS();
					somaIR += relacao.calcularIR();
					somaFGTS += relacao.calcularFGTS();
					somaSalarioLiquido += relacao.calcularSalarioLiquido();
				}
				System.out.println("------------ RESUMO FOLHA PAGAMENTO ------------" + "\nTOTAL Bruto: R$ "
						+ String.format("%.2f", somaSalarioBruto) + "\nTOTAL INSS: R$ "
						+ String.format("%.2f", somaINSS) + "\nTOTAL IRRF: R$ " + String.format("%.2f", somaIR)
						+ "\nTOTAL Líquido: R$ " + String.format("%.2f", somaSalarioLiquido) + "\nTOTAL FGTS: R$ "
						+ String.format("%.2f", somaFGTS));
				break;

			case (-1):
				break;
			default:
				System.out.println("Opcao invalida!! Selecione outra.");
			}

			System.out.println("\n* * * Pressione algo para continar...");
			System.in.read();

		} while (opt != -1);

		System.out.println("Saiu do sistema...");

		sc.close();
	}
}
