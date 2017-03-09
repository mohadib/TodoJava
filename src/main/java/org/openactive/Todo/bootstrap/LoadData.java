package org.openactive.Todo.bootstrap;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public abstract class LoadData implements ApplicationListener< ContextRefreshedEvent >
{
  private final Logger LOG = LoggerFactory.getLogger( getClass() );

  @Value( "${app.env:PROD}" )
  private String env;

  @Value( "${jpa.hbm2ddl.auto}" )
  private String hbm2ddl;

  public void onApplicationEvent( ContextRefreshedEvent contextRefreshedEvent )
  {
    LOG.info( "Context Refresh Event received" );
    LOG.info( "Environment: {}, hbm2ddl: {}", env, hbm2ddl );
    if ( !"DEV".equalsIgnoreCase( env ) )
    {
      LOG.info( "Env is not DEV, not calling load()" );
      return;
    }

    if ( !"create-drop".equalsIgnoreCase( hbm2ddl ) )
    {
      LOG.info( "hbm2ddl is not create-drop, not calling load()" );
      return;
    }
    if ( !contextRefreshedEvent.getApplicationContext().getDisplayName().contains( "Root" ) )
    {
      LOG.info( "Non root context event, not calling load()" );
      return;
    }

    LOG.info( "Calling load()" );
    load();
    LOG.info( "Load complete" );
  }

  public abstract void load();
}
