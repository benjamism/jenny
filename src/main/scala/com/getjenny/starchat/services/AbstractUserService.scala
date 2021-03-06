package com.getjenny.starchat.services

/**
  * Created by Angelo Leto <angelo@getjenny.com> on 4/12/17.
  */

import com.getjenny.starchat.entities._
import com.getjenny.starchat.services.auth.AbstractStarChatAuthenticator

import scala.concurrent.Future

abstract class AbstractUserService {
  def create(user: User): Future[IndexDocumentResult]
  def update(id: String, user: UserUpdate): Future[UpdateDocumentResult]
  def delete(id: String): Future[DeleteDocumentResult]
  def read(id: String): Future[User]
  def genUser(id: String, user: UserUpdate, authenticator: AbstractStarChatAuthenticator): Future[User]
}