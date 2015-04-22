import com.google.inject.{Guice, AbstractModule}
import database.UserStorage
import database.implementations.MongoDBUserImpl
import play.api.GlobalSettings

/**
 * Created by anders on 22/04/15.
 */
object Global extends GlobalSettings {
  /**
   * Bind types such that whenever TextGenerator is required, an instance of WelcomeTextGenerator will be used.
   */
  val injector = Guice.createInjector(new AbstractModule {
    protected def configure() {
      bind(classOf[UserStorage]).to(classOf[MongoDBUserImpl])
    }
  })

  /**
   * Controllers must be resolved through the application context. There is a special method of GlobalSettings
   * that we can override to resolve a given controller. This resolution is required by the Play router.
   */
  override def getControllerInstance[A](controllerClass: Class[A]): A = injector.getInstance(controllerClass)
}
