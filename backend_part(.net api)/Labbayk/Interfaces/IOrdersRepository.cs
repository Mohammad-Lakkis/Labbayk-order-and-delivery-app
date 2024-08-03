using Labbayk.Dto;
using Labbayk.Models;

namespace Labbayk.Interfaces
{
    public interface IOrdersRepository
    {
        ICollection<Order> GetOrders();
        ICollection<Order> GetOrdersByDriver(int driverId);
        Order GetOrder(string name);
        bool CreateOrder(ICollection<ItemDto> items, Order order, string driverName);
        bool DeleteOrder(string name);
        bool Save();
    }
}
