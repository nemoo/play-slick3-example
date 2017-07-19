package modules

import com.google.inject.{AbstractModule, Provides}
import com.mohiva.play.silhouette.api.actions.{SecuredErrorHandler, UnsecuredErrorHandler}
import com.mohiva.play.silhouette.api.crypto._
import com.mohiva.play.silhouette.api.services.{AuthenticatorService, IdentityService}
import com.mohiva.play.silhouette.api.util._
import com.mohiva.play.silhouette.api.{Environment, EventBus, Silhouette, SilhouetteProvider}
import com.mohiva.play.silhouette.crypto.{JcaCrypter, JcaCrypterSettings, JcaSigner, JcaSignerSettings}
import com.mohiva.play.silhouette.impl.authenticators.{CookieAuthenticator, _}
import com.mohiva.play.silhouette.impl.util.{DefaultFingerprintGenerator, SecureRandomIDGenerator}
import com.mohiva.play.silhouette.password.BCryptPasswordHasher
import com.mohiva.play.silhouette.persistence.daos.{DelegableAuthInfoDAO, InMemoryAuthInfoDAO}
import models.User
import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._
import net.codingwell.scalaguice.ScalaModule
import play.api.Configuration
import play.api.mvc.CookieHeaderEncoding
import utils.{AuthEnv, ErrorHandler, UserService}

import scala.concurrent.ExecutionContext.Implicits.global



class SilhouetteModule extends AbstractModule with ScalaModule{
  override def configure(): Unit = {
    bind[Silhouette[AuthEnv]].to[SilhouetteProvider[AuthEnv]]
    bind[SecuredErrorHandler].to[ErrorHandler]
    bind[UnsecuredErrorHandler].to[ErrorHandler]
    bind[IdentityService[User]].to[UserService]
    bind[IDGenerator].toInstance(new SecureRandomIDGenerator())
    bind[PasswordHasher].toInstance(new BCryptPasswordHasher())
    bind[FingerprintGenerator].toInstance(new DefaultFingerprintGenerator(false))
    bind[EventBus].toInstance(EventBus())
    bind[Clock].toInstance(Clock())

    bind[DelegableAuthInfoDAO[PasswordInfo]].toInstance(new InMemoryAuthInfoDAO[PasswordInfo])
  }

  @Provides
  def provideEnvironment(
                          authenticatorService: AuthenticatorService[CookieAuthenticator],
                          eventBus: EventBus,
                          userService: UserService
                        ): Environment[AuthEnv] = {
    Environment[AuthEnv](userService, authenticatorService, Seq.empty, eventBus)
  }

  /**
    * Provides the authenticator service.
    *
    * @param signer The signer implementation.
    * @param crypter The crypter implementation.
    * @param cookieHeaderEncoding Logic for encoding and decoding `Cookie` and `Set-Cookie` headers.
    * @param fingerprintGenerator The fingerprint generator implementation.
    * @param idGenerator The ID generator implementation.
    * @param configuration The Play configuration.
    * @param clock The clock instance.
    * @return The authenticator service.
    */
  @Provides
  def provideAuthenticatorService(
                                   signer: JcaSigner,
                                   crypter: JcaCrypter,
                                   cookieHeaderEncoding: CookieHeaderEncoding,
                                   fingerprintGenerator: FingerprintGenerator,
                                   idGenerator: IDGenerator,
                                   configuration: Configuration,
                                   clock: Clock): AuthenticatorService[CookieAuthenticator] = {

    val config = configuration.underlying.as[CookieAuthenticatorSettings]("silhouette.authenticator")
    val authenticatorEncoder = new CrypterAuthenticatorEncoder(crypter)

    new CookieAuthenticatorService(config, None, signer, cookieHeaderEncoding, authenticatorEncoder, fingerprintGenerator, idGenerator, clock)
  }


  /**
    * Provides the cookie signer for the authenticator.
    *
    * @param configuration The Play configuration.
    * @return The cookie signer for the authenticator.
    */
  @Provides
  def provideAuthenticatorCookieSigner(configuration: Configuration): JcaSigner = {
    val config = configuration.underlying.as[JcaSignerSettings]("silhouette.authenticator.signer")
    new JcaSigner(config)
  }

  /**
    * Provides the crypter for the authenticator.
    *
    * @param configuration The Play configuration.
    * @return The crypter for the authenticator.
    */
  @Provides
  def provideAuthenticatorCrypter(configuration: Configuration): JcaCrypter = {
    val config = configuration.underlying.as[JcaCrypterSettings]("silhouette.authenticator.crypter")
    new JcaCrypter(config)
  }
}
