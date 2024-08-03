using Labbayk.Data;
using Labbayk.Interfaces;
using Labbayk.Models;

namespace Labbayk.Repository
{
    public class DriversRepository : IDriversRepository
    {
        private readonly DataContext _context;

        public DriversRepository(DataContext context)
        {
            _context = context;
        }

        public Driver GetDriverByName(string name)
        {
            return _context.Drivers.Where(d => d.Name == name).FirstOrDefault();
        }

        public ICollection<Driver> GetDrivers()
        {
            return _context.Drivers.OrderBy(x => x.Name).ToList();
        }
    }
}
