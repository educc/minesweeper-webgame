import com.google.inject.AbstractModule
import com.me.minesweeper.api.services.{GameService, GameServiceInMemory}

import java.time.Clock

class Module extends AbstractModule {

  override def configure() = {
    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
    bind(classOf[GameService]).to(classOf[GameServiceInMemory])
  }

}
