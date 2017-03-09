package org.openactive.Todo.misc;

import java.util.List;

public class PageableResult<T>
{
  private List<T> items;
  private long count;

  public PageableResult( List< T > items, long count )
  {
    this.items = items;
    this.count = count;
  }

  public List< T > getItems()
  {
    return items;
  }

  public long getCount()
  {
    return count;
  }
}
