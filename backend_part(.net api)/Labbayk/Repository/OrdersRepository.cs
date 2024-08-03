using AutoMapper;
using Labbayk.Data;
using Labbayk.Dto;
using Labbayk.Interfaces;
using Labbayk.Models;

namespace Labbayk.Repository
{
    public class OrdersRepository : IOrdersRepository
    {

        private readonly DataContext _context;
        private readonly IMapper _mapper;

        public OrdersRepository(DataContext context, IMapper mapper)
        {
            _context = context;
            _mapper = mapper;
        }

        public bool CreateOrder(ICollection<ItemDto> items, Order order, string driverName)
        {
            
            Driver driver = _context.Drivers.Where(x => x.Name == driverName).FirstOrDefault();
            order.Driver = driver;
            _context.Add(order);
            Save();
            foreach (var item in items)
            {
                order.OrderItems.Add(new OrderItem { ItemId = item.Id, OrderId = order.Id });
            }
            return Save();
        }

        public bool DeleteOrder(string name)
        {
            _context.Remove(GetOrder(name));
            return Save();
        }

        public Order GetOrder(string name)
        {
            return _context.Orders.Where(o => o.CustomerName == name).FirstOrDefault();
        }

        public ICollection<Order> GetOrders()
        {
            throw new NotImplementedException();
        }

        public ICollection<Order> GetOrdersByDriver(int driverId)
        {
            return _context.Orders.OrderBy(o => o.CustomerName).Where(o => o.Driver.Id == driverId).ToList();
        }

        public bool Save()
        {
            var saved = _context.SaveChanges();
            return saved > 0 ? true : false;
        }
    }
}
