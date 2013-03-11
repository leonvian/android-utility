package br.com.lvc.utility.taskcontrol;

import br.com.lvc.utility.exceptions.AndroidAppException;


/** 
 * Representa uma tarefa que deve ser executada  em seguida os dados gerados por essa tarefa podem se processados.
 * @author Administrador
 *
 */
public interface SimpleTask {

	/**
	 * Executa a tarefa que ela � respons�vel
	 * @return o resultado da tarefa, se existir
	 * @throws AndroidAppException
	 */
	public abstract TaskResult executeTask() throws AndroidAppException;

	/**
	 * Assim que acaba o processo esse evento ocorre com o resultado do mesmo.
	 * @param taskResult
	 */
	public abstract void processAfterTask(TaskResult taskResult);


	public abstract void processAfterFailTask(AndroidAppException e);


}
