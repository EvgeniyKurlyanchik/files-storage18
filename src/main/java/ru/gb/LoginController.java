package ru.gb;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ru.gb.commons.AuthResult;
import ru.gb.commons.DataPackage;

import static java.lang.Thread.currentThread;

import static ru.gb.UIutils.showError;
import static ru.gb.commons.AuthResult.Result.OK;


public class LoginController
		implements Initializable
{

  private NetConnection conn;
  private ExecutorService exec;
  private Runnable authHandler;

  @FXML
  TextField login;

  @FXML
  PasswordField password;

  @FXML
  VBox rootElem;

  private Runnable callback;


  @Override
  public void initialize(URL location, ResourceBundle resources)
  {
	conn = Main.getNetConnection();
	authHandler = new AuthHandler();
	exec = Executors.newSingleThreadExecutor();
	exec.execute(authHandler);
  }


  public void auth(ActionEvent event)
  {
	try
	{
	  conn.auth(login.getText(), password.getText());
	}
	catch (NetConnection.SendDataException e)
	{
	  showError(e.getCause().getMessage());
	}
  }


  public void setCallback(Runnable cb)
  {
	callback = cb;
  }


  private class AuthHandler
		  implements Runnable
  {

	@Override
	public void run()
	{
	  try
	  {
		while (!currentThread().isInterrupted())
		{
		  DataPackage response = conn.getResponseFromServer();

		  if (response instanceof AuthResult)
		  {
			AuthResult ar = (AuthResult) response;
			if (ar.getResult() == OK)
			  break;

			showError("Autorization failed");
		  }
		}

		callback.run();
	  }
	  catch (NetConnection.ServerResponseException e)
	  {
		e.printStackTrace();
	  }
	}

  }

}